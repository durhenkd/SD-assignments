
import { AccountActionType as typ } from "../action-types/ActionType"

interface LogOutAction {
  type: typ.LOGOUT
}

interface UserLogInAction {
  type: typ.USER_LOGIN,
  payload: {
    id: number,
    name: string
  }
}

interface AdminLogInAction {
  type: typ.ADMIN_LOGIN,
  payload: {
    id: number,
    name: string
  }
}

export type AccountAction = LogOutAction | UserLogInAction | AdminLogInAction