import axios from "axios";
import { GET_MAPPED_ERRORS, SEARCH_FOR_TRAINS } from "./types";

export const searchForTrain = (
  departureStation,
  destinationStation,
  departureDate
) => async dispatch => {
  try {
    const res = await axios.get(
      `/api/train/search?departureStation=${departureStation}&destinationStation=${destinationStation}&departureDate=${departureDate}`
    );
    dispatch({
      type: GET_MAPPED_ERRORS,
      payload: {}
    });
    dispatch({
      type: SEARCH_FOR_TRAINS,
      payload: res.data
    });
  } catch (err) {
    dispatch({
      type: GET_MAPPED_ERRORS,
      payload: err.response.data
    });
  }
};
