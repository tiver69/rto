import { combineReducers } from "redux";
import ticketReducer from "./ticketReducer";
import stationReducer from "./stationReducer";
import trainReducer from "./trainReducer";

export default combineReducers({
  ticket: ticketReducer,
  station: stationReducer,
  train: trainReducer
});
