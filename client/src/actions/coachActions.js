import axios from "axios";
import { SEARCH_FOR_COACES } from "./types";

export const searchForCoaches = (trainId, departureDate) => async dispatch => {
  const res = await axios.get(
    `/api/coach/search?trainId=${trainId}&departureDate=${departureDate}`
  );
  dispatch({
    type: SEARCH_FOR_COACES,
    payload: res.data
  });
};
