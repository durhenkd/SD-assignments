import { combineReducers } from "redux";

import accountReducer from "./accountReduces";
import cartReducer from "./cartReducer";

const rootReducer = combineReducers({
  account: accountReducer,
  cart: cartReducer
});

export default rootReducer;

export type State = ReturnType<typeof rootReducer>

