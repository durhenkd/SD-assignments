import { Link } from "react-router-dom";


const NoMatch = () => {
  return <div className="flex flex-col flex-wrap justify-center items-center">
    <h1>Page not found</h1>
    <p>Please navigate to the <Link to="/">Main Page</Link></p>
  </div>
}

export default NoMatch;