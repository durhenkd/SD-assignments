import { FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import * as IStyle from "./simple/InputStyle";
import * as BStyle from "./simple/ButtonStyle";
import { login } from "./axios";
import React from "react";

import { actionCreators } from "./state/index";
import { bindActionCreators } from "redux";
import { useDispatch } from "react-redux";

type LogInFormState = {
  username: string;
  password: string;
};

const LogIn = () => {
  const navigate = useNavigate();

  const [error, setError] = React.useState<string>("");
  const [formState, setFormState] = React.useState<LogInFormState>({
    username: "",
    password: "",
  });

  const dispatch = useDispatch();
  const { adminLogin, userLogin } = bindActionCreators(
    actionCreators,
    dispatch
  );

  // ! ========== FORM HANDLER ==========
  const formHandler = (event: FormEvent) => {
    event.preventDefault();

    setError("");

    if (formState.password.length < 8 || formState.username === "") {
      setError("All field must be completed");
      return;
    }

    login(formState.username, formState.password).then((data) => {
      if (data.isAdmin) {
        adminLogin(data.name, data.id); //! redux
        navigate("/admin");
      } else {
        userLogin(data.name, data.id); //! redux
        navigate("/restaurant");
      }
    });
  };

  return (
    <div className="w-1/3 h-full flex flex-col flex-wrap justify-center items-center">
      <form onSubmit={formHandler} className="w-full">
        <input
          className={`grow w-full ${IStyle.getToggleStyle("neutral")}`}
          type="text"
          placeholder="Username"
          value={formState.username}
          onChange={(v) => {
            setFormState({ ...formState, username: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getToggleStyle("neutral")}`}
          type="password"
          placeholder="Password"
          value={formState.password}
          onChange={(v) => {
            setFormState({ ...formState, password: v.target.value }); //! making a copy then overwriting
          }}
        />
        {error && <h4 className="text-red-700 text-lg">{error}</h4>}
        <button
          onClick={() => {}}
          className={`grow w-full ${BStyle.getFillHoverOutline("primary")}`}
        >
          Log In
        </button>
      </form>
      <div className="flex flex-row w-full justify-center gap-2">
        <button
          onClick={() => {
            navigate("/register");
          }}
          className={`grow w-full ${BStyle.getOutlineHoverFill("neutral")}`}
        >
          Register!
        </button>
        <button
          onClick={() => {
            navigate("/admin/register");
          }}
          className={`shrink grow w-full ${BStyle.getEmptyHoverOutline(
            "neutral"
          )}`}
        >
          Are you a restaurant?
        </button>
      </div>
    </div>
  );
};

export default LogIn;
