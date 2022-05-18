import { Link } from "react-router-dom";

export type Restaurant = {
  id: number;
  name: string;
  location: string;
  deliveryLocations: string;
  photoUrl: string;
};

const RestaurantItem = (props: Restaurant) => {
  //todo fetch imageURL | tailwind - grow-0
  return (
    <Link className="w-full cursor-pointer" to={`/restaurant/${props.id}`}>
      <div className="flex flex-row grow hover:bg-neutral-50 rounded-3xl">
        <img
          className="grow-0 w-96 h-48"
          src="/default/restaurantItem.png"
          alt="restaurant_photo"
        ></img>
        <div className="flex flex-col w-full p-9 justify-center">
          <h3 className="font-thin text-neutral-800 text-4xl">{props.name}</h3>
          <h6 className="font-semibold text-xl text-neutral-600 ">{props.location}</h6>
          <p>{`Delivering in: ${props.deliveryLocations}`}</p>
        </div>
      </div>
    </Link>
  );
};

export default RestaurantItem;
