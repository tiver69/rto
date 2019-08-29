import { combineReducers } from "redux";
import ticketReducer from "./ticketReducer";
import stationReducer from "./stationReducer";
import trainReducer from "./trainReducer";
import searchReducer from "./searchReducer";
import coachReducer from "./coachReducer";
import passengerReducer from "./passengerReducer";

export default combineReducers({
  ticket: ticketReducer,
  station: stationReducer,
  train: trainReducer,
  coach: coachReducer,
  passenger: passengerReducer,
  search: searchReducer
});
