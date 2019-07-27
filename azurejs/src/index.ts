import { Handler, Logger, Requests } from "../../scalajs/dist/handler";
import { isRight } from "../../scalajs/dist/helper";

const handler = new Handler(
  new Logger(v => console.log(v), v => console.warn(v))
);
const result = handler.run(new Requests("fooBar"));
if (isRight(result)) {
  console.log(result.value$2.result);
} else {
  console.log("error");
}
