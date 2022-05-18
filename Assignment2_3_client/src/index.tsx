import React from "react";
import * as ReactDOM from "react-dom/client";
import App from "./App";
import "./index.css";
import { Provider } from "react-redux";

import { store } from "./state/store";

const rootElement = document.getElementById("root");

if (rootElement == null) throw Error("Root element doesn't exist");

const root: HTMLElement = rootElement;

ReactDOM.createRoot(root).render(
  <Provider store={store}>
    <App />
  </Provider>
);
