import axios from "axios";
import { SEARCH_FOR_COACH, GET_STRING_ERROR } from "./types";

export const searchForCoach = (
  trainId,
  departureDate,
  coachNumber
) => async dispatch => {
  try {
    const res = await axios.get(
      `/api/coach/search?trainId=${trainId}&departureDate=${departureDate}&coachNumber=${coachNumber}`
    );
    dispatch({
      type: SEARCH_FOR_COACH,
      payload: res.data
    });
    dispatch({
      type: GET_STRING_ERROR,
      payload: ""
    });
  } catch (err) {
    dispatch({
      type: GET_STRING_ERROR,
      payload: err.response.data
    });
  }
};
