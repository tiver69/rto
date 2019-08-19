import { SEARCH_FOR_COACES } from "../actions/types";

const initialState = {
  coach: []
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SEARCH_FOR_COACES:
      return {
        ...state,
        coach: action.payload
      };

    default:
      return state;
  }
}
