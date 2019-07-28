export interface Right<T> {
  value$2: T;
}

export interface Left<E = any> {}

export type Either<T, E = any> = Right<T> | Left<E>;

export const isRight = <T>(e: Either<T>): e is Right<T> =>
  e.constructor.toString().indexOf("Right") !== -1;
