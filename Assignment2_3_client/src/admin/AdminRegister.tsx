import * as IStyle from "../simple/InputStyle";
import * as BStyle from "../simple/ButtonStyle";

import React from "react";
import { useNavigate } from "react-router-dom";
import { login, spring_axios } from "../axios";
import { adminLogin } from "../state/action-creators";

type AdminRegisterFormState = {
  email: string;
  password: string;
  name: string;
  location: string;
  deliveryLocations: string;
  photoUrl: string;
};

const AdminRegister = () => {
  const navigate = useNavigate();

  const [error, setError] = React.useState<string>("");
  const [confPass, setConfPass] = React.useState<string>("");
  const [formState, setFormState] = React.useState<AdminRegisterFormState>({
    email: "",
    password: "",
    name: "",
    location: "",
    deliveryLocations: "",
    photoUrl: "NA",
  });

  const formHandler = (event: React.FormEvent) => {
    event.preventDefault();
    setError("");

    //* checking for validity
    if (
      formState.name === "" ||
      formState.deliveryLocations === "" ||
      formState.location === "" ||
      formState.photoUrl === "" ||
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

    console.log(formState);

    spring_axios
      .post("/register/admin", formState)
      .then((res) => {
        login(formState.email, formState.password).then((data) => {
          adminLogin(data.name, data.id);
          navigate("/admin");
        });
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
          className={`grow w-full ${IStyle.getToggleStyle("primary")}`}
          type="email"
          placeholder="Email"
          value={formState.email}
          onChange={(v) => {
            setFormState({ ...formState, email: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getToggleStyle("primary")}`}
          type="password"
          placeholder="Password"
          value={formState.password}
          onChange={(v) => {
            setFormState({ ...formState, password: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getSimpleStyle("primary")}`}
          type="password"
          placeholder="Confirm Password"
          value={confPass}
          onChange={(v) => {
            setConfPass(v.target.value);
          }}
        />
        <input
          className={`grow w-full ${IStyle.getToggleStyle("neutral")}`}
          type="text"
          placeholder="Restaurant Name"
          value={formState.name}
          onChange={(v) => {
            setFormState({ ...formState, name: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getToggleStyle("neutral")}`}
          type="text"
          placeholder="Restaurant Location"
          value={formState.location}
          onChange={(v) => {
            setFormState({ ...formState, location: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getToggleStyle("neutral")}`}
          type="text"
          placeholder="Delivery Locations"
          value={formState.deliveryLocations}
          onChange={(v) => {
            setFormState({ ...formState, deliveryLocations: v.target.value }); //! making a copy then overwriting
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
            navigate("/register");
          }}
          className={`grow w-full ${BStyle.getEmptyHoverOutline("neutral")}`}
        >
          Register as a user insead
        </button>
      </div>
    </div>
  );
};

export default AdminRegister;
