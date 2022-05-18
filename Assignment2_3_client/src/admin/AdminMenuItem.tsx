import { Food } from "../user/MenuItem";

const AdminMenuItem = (props: Food) => {
  return (
    <div className="w-full p-9 flex flex-col gap-2 hover:bg-neutral-50 transition ease-in-out rounded-2xl">
      <div className="w-full flex flex-row gap-6 items-end">
        <h2 className="font-bold text-5xl">{props.name}</h2>
        <h6 className="text-neutral-600">{props.foodCategory}</h6>
      </div>
        <p className="text-xl">{`${props.price} lei | ${props.description}`}</p>
    </div>
  );
};

export default AdminMenuItem;
