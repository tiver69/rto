import { combineReducers } from "redux";
import ticketReducer from "./ticketReducer";
import stationReducer from "./stationReducer";
import trainReducer from "./trainReducer";
import searchReducer from "./searchReducer";
import coachReducer from "./coachReducer";

export default combineReducers({
  ticket: ticketReducer,
  station: stationReducer,
  train: trainReducer,
  coach: coachReducer,
  search: searchReducer
});
