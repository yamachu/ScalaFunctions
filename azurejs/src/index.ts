import { Handler, Logger, Requests } from "../../scalajs/dist/handler";
import { isRight } from "../../scalajs/dist/helper";

module.exports.httpTrigger = async (context: any, req: any) => {
  const handler = new Handler(
    new Logger(v => context.log.info(v), v => context.log.warn(v))
  );
  const result = handler.run(new Requests(req.body.name));
  if (isRight(result)) {
    context.res = {
      body: result.value.result
    };
  } else {
    context.res = {
      status: 400,
      body: "Error"
    };
  }
};
