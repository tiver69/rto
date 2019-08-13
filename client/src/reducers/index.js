import { combineReducers } from "redux";
import ticketReducer from "./ticketReducer";
import stationReducer from "./stationReducer";

export default combineReducers({
  ticket: ticketReducer,
  station: stationReducer
});
