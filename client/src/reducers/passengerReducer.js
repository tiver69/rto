import { GET_PASSENGERS } from "../actions/types";

const initialState = {
  passengers: []
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_PASSENGERS:
      return {
        ...state,
        passengers: action.payload
      };

    default:
      return state;
  }
}
