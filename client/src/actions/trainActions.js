import axios from "axios";
import { SEARCH_FOR_TRAINS } from "./types";

export const searchForTrain = (
  departureStation,
  destinationStation,
  departureDate
) => async dispatch => {
  const res = await axios.get(
    `/api/train/search?departureStation=${departureStation}&destinationStation=${destinationStation}&departureDate=${departureDate}`
  );
  dispatch({
    type: SEARCH_FOR_TRAINS,
    payload: res.data
  });
};
