import { SEARCH_FOR_TRAINS } from "../actions/types";

const initialState = {
  trains: []
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SEARCH_FOR_TRAINS:
      return {
        ...state,
        trains: action.payload
      };

    default:
      return state;
  }
}
