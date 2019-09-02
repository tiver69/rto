import React from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import {
  savePassengerTicket,
  countTicketPrice
} from "../../actions/ticketActions";
import IcoMoon from "react-icomoon";

class ConfirmBuyingPopup extends React.Component {
  constructor() {
    super();
    this.onConfirmClick = this.onConfirmClick.bind(this);
    this.onAndContinueClick = this.onAndContinueClick.bind(this);
  }

  componentDidMount() {
    this.props.countTicketPrice(
      this.props.search.trainParam.id,
      this.props.coach.id,
      this.props.search.directionParam.departureStation.value,
      this.props.search.directionParam.destinationStation.value
    );
  }

  onAndReturnToHomeClick = placeNumber => {
    placeNumber.preventDefault();
    this.onConfirmClick(placeNumber);
    this.props.history.push("/home/1");
    // TO_DO: add id of current user
  };

  onAndContinueClick = placeNumber => {
    placeNumber.preventDefault();
    this.onConfirmClick(placeNumber);
    this.props.closePopup(placeNumber);
  };

  onConfirmClick = placeNumber => {
    placeNumber.preventDefault();
    const passenger = {
      id: 1
      // TO_DO: setup passenger id from security session
    };
    const train = {
      id: this.props.search.trainParam.id
    };
    const trainCoach = {
      id: this.props.coach.id,
      train: train
    };
    const departureStation = {
      id: this.props.search.directionParam.departureStation.value
    };
    const destinationStation = {
      id: this.props.search.directionParam.destinationStation.value
    };
    const newTicket = {
      passenger: passenger,
      departureStation: departureStation,
      destinationStation: destinationStation,
      departureDate: this.props.search.directionParam.departureDate,
      trainCoach: trainCoach,
      place: placeNumber.target.value,
      price: this.props.ticket.ticketPrice
    };
    this.props.savePassengerTicket(newTicket);
  };

  render() {
    return (
      <div className="popup">
        <div className="popup_inner">
          <h1>Your ticket</h1>

          <div className="col-ticket">
            <div className="listing-horizontal d-block d-md-flex form-search-wrap ticket">
              <div className="img d-block ticket-active-background-pic">
                <span className="category">
                  COACH - {this.props.coach.number}
                  <br />
                  PLACE - {this.props.text}
                  <br />
                  PRICE - {this.props.ticket.ticketPrice}₴
                  <br />
                </span>
              </div>

              <div className="lh-content t-align-left">
                <h3>
                  {this.props.search.directionParam.departureStation.label} -
                  {this.props.search.directionParam.destinationStation.label}
                </h3>

                <p>
                  <IcoMoon className="icon" icon="road" /> Train number #{" "}
                  {this.props.search.trainParam.id}
                </p>

                <p>
                  <IcoMoon className="icon" icon="clock" /> Departure Time -{" "}
                  {this.props.search.trainParam.departureTime}
                </p>

                <p>
                  <IcoMoon className="icon" icon="clock" /> Arrival -{" "}
                  {this.props.search.trainParam.arrivalTime}
                </p>

                <h3>
                  LAST_NAME FIRST_NAME
                  {/* TO_DO: get names from security */}
                </h3>
              </div>
            </div>
          </div>

          <div className="popup-row">
            <button
              className="col btn btn-primary-o"
              value={this.props.text}
              onClick={this.onAndContinueClick}
            >
              Confirm and CONTINUE buying
            </button>

            <button
              className="col btn btn-primary-o"
              value={this.props.text}
              onClick={this.onAndReturnToHomeClick}
            >
              Confirm and RETURN to home page
            </button>

            <button
              className="col btn btn-primary-o"
              onClick={this.props.closePopup}
            >
              Cansel
            </button>
          </div>
        </div>
      </div>
    );
  }
}

ConfirmBuyingPopup.propTypes = {
  search: PropTypes.object.isRequired,
  savePassengerTicket: PropTypes.func.isRequired,
  countTicketPrice: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  ticket: state.ticket,
  search: state.search
});

export default connect(
  mapStateToProps,
  { savePassengerTicket, countTicketPrice }
)(ConfirmBuyingPopup);
