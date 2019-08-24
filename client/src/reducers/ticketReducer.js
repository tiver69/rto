import {
  GET_PASSENGER_TICKETS,
  SAVE_NEW_TICKET,
  COUNT_TICKET_PRICE
} from "../actions/types";

const initialState = {
  tickets: [],
  ticket: {},
  ticketPrice: 0
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_PASSENGER_TICKETS:
      return {
        ...state,
        tickets: action.payload
      };
    case SAVE_NEW_TICKET:
      return {
        ...state,
        ticket: action.payload
      };
    case COUNT_TICKET_PRICE:
      return {
        ...state,
        ticketPrice: action.payload
      };

    default:
      return state;
  }
}
