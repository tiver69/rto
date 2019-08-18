import { SEARCH_FOR_COACES } from "../actions/types";

const initialState = {
  coaches: []
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SEARCH_FOR_COACES:
      return {
        ...state,
        coaches: action.payload
      };

    default:
      return state;
  }
}
