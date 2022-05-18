import LogIn from "./LogIn";
import NavBar from "./NavBar";
import "./index.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import NoMatch from "./NoMatch";
import UserRegister from "./user/UserRegister";
import RestaurantList from "./user/RestaurantList";
import Menu from "./user/Menu";
import OrderList from "./user/OrderList";
import AdminHome from "./admin/AdminHome";
import AdminRegister from "./admin/AdminRegister";
import AdminAddFood from "./admin/AdminAddFood";
import AdminOrders from "./admin/AdminOrders";
import Welcome from "./Welcome";
import Cart from "./user/Cart";

const App = () => {
  return (
    <BrowserRouter>
      <div className="bg-neutral-10 flex flex-col w-screen h-screen overflow-y-scroll">
        <NavBar username="Default" imageURL="placeholder" />
        <div className="pt-20 w-full h-full flex flex-col items-center">
          <Routes>
            <Route path="/" element={<Welcome />} />
            <Route path="/login" element={<LogIn />} />
            <Route path="/register" element={<UserRegister />} />

            <Route path="/cart" element={<Cart />}/>
            <Route path="/orders" element={<OrderList />}/>

            <Route path="/restaurant" element={<RestaurantList />}/>
            <Route path="/restaurant/:restaurant_id" element={<Menu />} />

            <Route path="/admin" element={<AdminHome />} />
            <Route path="/admin/register" element={<AdminRegister />} />
            <Route path="/admin/add" element={<AdminAddFood />} />
            <Route path="/admin/orders" element={<AdminOrders />} />

            <Route path="*" element={<NoMatch />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default App;
