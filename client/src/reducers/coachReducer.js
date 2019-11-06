import { SEARCH_FOR_COACH } from "../actions/types";

const initialState = {
  coach: []
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SEARCH_FOR_COACH:
      return {
        ...state,
        coach: action.payload
      };

    default:
      return state;
  }
}
