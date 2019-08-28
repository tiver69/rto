import axios from "axios";
import { GET_PASSENGERS } from "./types";

export const getPassengers = () => async dispatch => {
  const res = await axios.get("/api/passenger");
  dispatch({
    type: GET_PASSENGERS,
    payload: res.data
  });
};
