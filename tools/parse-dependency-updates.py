from __future__ import print_function

import functools
import re
from argparse import ArgumentParser

PACKAGE_RE = re.compile(r'^(?P<package_name>.+)\s:\s(?P<version>.+)$')
VERSION_RE = re.compile(r'^(?P<current>.+)\s+\->\s+(?P<next>.+)$')


def _split_package_and_version(s):
    name, version = PACKAGE_RE.match(s).group('package_name', 'version')
    current, next_ = VERSION_RE.match(version.strip()).group('current', 'next')
    return name.strip(),  '{} -> {}'.format(current.strip(), next_.strip())


def get_projet_outdated(path):
    with open(path) as f:
        header = f.readline().strip()
        if len(header) == 0:
            return '', []

        return header, [_split_package_and_version(s.strip()) for s in f]


def uniq_outdated_package(pp):
    merged = functools.reduce(lambda x, y: x + y, map(lambda v: v[1], pp))
    return sorted(set(_format_str(merged)))


def _format_str(package_version_list):
    if len(package_version_list) == 0:
        return []
    ln = max([len(x[0]) for x in package_version_list])
    fmt = '{name:<{padding}} {version}'

    return [fmt.format(name=v[0], padding=ln, version=v[1]) for v in package_version_list]


def format_output(raw_packages, uniqued_packages):
    for header, packages in raw_packages:
        print('### {}'.format(header.split(' ')[-1]))
        print('```')
        [print(v) for v in _format_str(packages)]
        print('```')
        print()

    print('### uniqued packages')
    print('```')
    [print(s) for s in uniqued_packages]
    print('```')


if __name__ == "__main__":
    parser = ArgumentParser()
    parser.add_argument('files', nargs='+', help='file paths')

    args = parser.parse_args()

    project_packages = list(
        filter(lambda x: x[0] != '', [get_projet_outdated(p) for p in args.files]))
    uniqued_packages = uniq_outdated_package(project_packages)

    format_output(project_packages, uniqued_packages)