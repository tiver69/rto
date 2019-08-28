import { GET_PASSENGERS, REMOVE_PASSENGER } from "../actions/types";

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
    case REMOVE_PASSENGER:
      return {
        ...state,
        passengers: state.passengers.filter(
          passenger => passenger.id !== action.payload
        )
      };
    default:
      return state;
  }
}
