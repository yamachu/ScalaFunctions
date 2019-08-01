export interface Right<T> {
  ok: true;
  value: T;
}

export interface Left<E = any> {
  ok: false;
  error: E;
}

export type Either<T, E = any> = Right<T> | Left<E>;

export const isRight = <T>(e: Either<T>): e is Right<T> => e.ok;
