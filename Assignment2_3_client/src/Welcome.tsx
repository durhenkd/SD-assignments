import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Welcome = () =>{

  const navigate = useNavigate();

  useEffect(() => {
    //todo authentication when app loads
    
    navigate("/login");

  });

  return <div></div>
}

export default Welcome;