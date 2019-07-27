import { Either } from "./helper";

export class Requests {
  constructor(name: string);
}

export class Response {
  result: string;
}

export class Logger {
  constructor(info: (_: string) => void, warn: (_: string) => void);
}

export class Handler {
  constructor(logger?: Logger);
  run: (requests: Requests) => Either<Response>;
}
