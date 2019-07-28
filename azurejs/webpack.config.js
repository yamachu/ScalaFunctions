const path = require('path');
const output_path = 'app';

module.exports = function(env, argv) {
    return [
        {
            target: 'node',
            entry: './src/index.ts',
            output: {
                path: path.resolve(__dirname, output_path),
                filename: 'index.js',
                libraryTarget: 'commonjs',
            },
            resolve: {
                extensions: ['.ts', '.js'],
                modules: ['node_modules'],
            },
            module: {
                rules: [
                    {
                        test: /\.ts$/,
                        exclude: /node_modules/,
                        use: ['ts-loader'],
                    },
                ],
            },
            optimization: {
                // Either-Rightのコンストラクタの名前を残すため
                minimize: false
            }
        },
    ];
};
