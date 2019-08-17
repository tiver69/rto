import axios from "axios";
import { GET_STATIONS } from "./types";

export const getStations = () => async dispatch => {
  const res = await axios.get("/api/station/select");
  dispatch({
    type: GET_STATIONS,
    payload: res.data
  });
};
