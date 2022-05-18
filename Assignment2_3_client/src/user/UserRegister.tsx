import * as React from "react";
import { useNavigate } from "react-router-dom";
import * as IStyle from "../simple/InputStyle";
import * as BStyle from "../simple/ButtonStyle";
import { spring_axios } from "../axios";

type UserRegisterFormState = {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
};

const UserRegister = () => {
  const navigate = useNavigate();

  const [error, setError] = React.useState<string>("");
  const [confPass, setConfPass] = React.useState<string>("");
  const [formState, setFormState] = React.useState<UserRegisterFormState>({
    username: "",
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  const formHandler = (event: React.FormEvent) => {
    event.preventDefault();

    setError("");

    //* checking for validity
    if (
      formState.username === "" ||
      formState.firstName === "" ||
      formState.lastName === "" ||
      formState.email === "" ||
      formState.password === "" ||
      confPass === ""
    ) {
      setError("Please fill out every field of the form");
      return;
    }

    if (
      formState.password.length < 8 ||
      formState.password.match(`[0-9]`) == null ||
      formState.password.match("[~!@#$%^&*()_=+,/;'<>?:{}]") == null ||
      formState.password.match(`[A-Za-z]`) == null
    ) {
      setError(
        "Password must have at least 8 characters, contain a special character, and number."
      );
      return;
    }

    if (formState.password !== confPass) {
      return;
    }

    spring_axios
      .post("/register/user", formState)
      .then((res) => {
        //todo remember the user id with redux
        navigate("/restaurant");
      })
      .catch((err) => {
        setError(err.message);
        console.log(err);
      });
  };

  return (
    <div className="w-1/3 h-full flex flex-col flex-wrap justify-center items-center">
      <form onSubmit={formHandler} className="w-full">
        <img src="" alt="Welcome to HungerHalt" />
        <input
          className={`grow w-full ${IStyle.getSimpleStyle("neutral")}`}
          type="text"
          placeholder="First Name"
          value={formState.firstName}
          onChange={(v) => {
            setFormState({ ...formState, firstName: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getSimpleStyle("neutral")}`}
          type="text"
          placeholder="Last Name"
          value={formState.lastName}
          onChange={(v) => {
            setFormState({ ...formState, lastName: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getToggleStyle("primary")}`}
          type="text"
          placeholder="Username"
          value={formState.username}
          onChange={(v) => {
            setFormState({ ...formState, username: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getToggleStyle("primary")}`}
          type="email"
          placeholder="Email"
          value={formState.email}
          onChange={(v) => {
            setFormState({ ...formState, email: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getSimpleStyle("neutral")}`}
          type="password"
          placeholder="Password"
          value={formState.password}
          onChange={(v) => {
            setFormState({ ...formState, password: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getSimpleStyle("neutral")}`}
          type="password"
          placeholder="Confirm Password"
          value={confPass}
          onChange={(v) => {
            setConfPass(v.target.value);
          }}
        />
        {confPass !== formState.password && (
          <h4 className="text-red-700 text-lg">Passwords don't match!</h4>
        )}
        {error && <h4 className="text-red-700 text-lg">{error}</h4>}
        <button
          onClick={() => {}}
          className={`grow w-full ${BStyle.getFillHoverOutline("primary")}`}
        >
          Register
        </button>
      </form>
      <div className="flex flex-row w-full justify-center gap-2">
        <button
          onClick={() => {
            navigate("/login");
          }}
          className={`grow w-1/2 ${BStyle.getOutlineHoverFill("neutral")}`}
        >
          Log In instead
        </button>
        <button
          onClick={() => {
            navigate("/admin/register");
          }}
          className={`grow w-1/2 ${BStyle.getEmptyHoverOutline("neutral")}`}
        >
          Are you a restaurant?
        </button>
      </div>
    </div>
  );
};

export default UserRegister;
