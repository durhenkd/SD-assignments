import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { spring_axios } from "../axios";
import { getSimpleStyle } from "../simple/InputStyle";
import { State } from "../state";
import RestaurantItem, { Restaurant } from "./RestaurantItem";

const RestaurantList = () => {
  const [restaurantList, setRestaurantList] = useState<Restaurant[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>("");

  const { id, isAdmin } = useSelector((state: State) => state.account);
  const navigate = useNavigate();

  useEffect(() => {
    if (id === -1 || isAdmin) {
      navigate("/login");
    } else {
      spring_axios.get("/user/restaurant").then((res: any) => {
        setRestaurantList(res.data);
      });
    }
  }, [id, isAdmin, navigate]);

  return (
    <div className="flex flex-col w-5/6 justify-center items-center gap-5 px-10 py-5 ">
      <input
        className={`w-full mb-10 ${getSimpleStyle("neutral")}`}
        value={searchTerm}
        onChange={(e) => {
          setSearchTerm(e.target.value);
        }}
        placeholder="Search"
        type="text"
      />

      {!restaurantList && "Loading..."}
      {restaurantList &&
        restaurantList
          .filter((r) => {
            if (searchTerm === "") return true;
            else return r.name.toLowerCase().includes(searchTerm.toLowerCase());
          })
          .map((r) => (
            <RestaurantItem
              id={r.id}
              deliveryLocations={r.deliveryLocations}
              name={r.name}
              location={r.location}
              key={r.id}
              photoUrl={r.photoUrl}
            />
          ))}
    </div>
  );
};

export default RestaurantList;
