import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { State } from "./state";

import { actionCreators } from "./state/index";
import { bindActionCreators } from "redux";
import { useDispatch } from "react-redux";

type NavBarProps = {
  username: string;
  imageURL: string;
};

export default function NavBar(props: NavBarProps) {
  const [navbarOpen, setNavbarOpen] = useState(false);
  const { id, name, isAdmin } = useSelector((state: State) => state.account);
  const { cart } = useSelector((state: State) => state.cart);

  const dispatch = useDispatch();
  const { logOut } = bindActionCreators(actionCreators, dispatch);

  const getCartLink = () => {
    let items_nr = cart.reduce(
      (prev, item) => {
        prev.price += item.price * item.quantity;
        prev.quantity += item.quantity;
        return prev;
      },
      { quantity: 0, price: 0 }
    );

    return (
      <li className="nav-item">
        <Link
          className="ml-2 text-lg font-bold upp"
          to="/cart"
        >{`Cart(${items_nr.quantity})`}</Link>
      </li>
    );
  };

  const getOrdersLink = () => {
    return (
      <li className="nav-item">
        <Link className="ml-2 text-lg font-bold upp" to="/orders">
          Your Orders
        </Link>
      </li>
    );
  };

  const getLogOutBtn = () => {
    return (
      <li>
        <button
          className="ml-2 text-lg font-bold"
          onClick={() => {
            logOut();
          }}
        >
          Log Out
        </button>
        ;
      </li>
    );
  };

  return (
    <nav className="absolute w-full flex flex-wrap items-center justify-between px-2 py-3 bg-neutral-10 mb-3">
      <div className="reltive container px-4 mx-auto flex flex-wrap items-center justify-between">
        <div className="w-full relative flex justify-between lg:w-auto lg:static lg:block lg:justify-start">
          <Link
            className="text-lg font-bold leading-relaxed inline-block mr-4 py-2 whitespace-nowrap  text-white"
            to={isAdmin ? "/admin" : "/restaurant"}
          >
            BellyBear
          </Link>
          <button
            className="text-white cursor-pointer text-xl leading-none px-3 py-1 border border-solid border-transparent rounded bg-transparent block lg:hidden outline-none focus:outline-none"
            type="button"
            onClick={() => setNavbarOpen(!navbarOpen)}
          >
            Open
          </button>
        </div>
        <div
          className={`lg:flex flex-grow  items-center" ${
            navbarOpen ? " flex" : " hidden"
          }`}
        >
          <ul className="flex flex-row gap-6 lg:flex-row list-none lg:ml-auto">
            <li className="nav-item">
              <span className="ml-2 text-lg font-bold">{`Hi! ${name}`}</span>
            </li>
            {!isAdmin && id !== -1 ? getCartLink() : ""}
            {!isAdmin && id !== -1 ? getOrdersLink() : ""}
            {id !== -1 ? getLogOutBtn() : ""}
          </ul>
        </div>
      </div>
    </nav>
  );
}
