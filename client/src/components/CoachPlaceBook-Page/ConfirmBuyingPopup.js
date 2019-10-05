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
    const { mappedErrors } = this.props.error;
    if (!mappedErrors) this.props.history.push("/home/1");
    // TO_DO: add id of current user
  };

  onAndContinueClick = placeNumber => {
    placeNumber.preventDefault();
    this.onConfirmClick(placeNumber);

    const { mappedErrors } = this.props.error;
    if (!mappedErrors) this.props.closePopup(placeNumber);
  };

  onConfirmClick = placeNumber => {
    placeNumber.preventDefault();
    const passenger = {
      id: 1
      // TO_DO: setup passenger id from security session
    };
    const train = {
      id: 1234
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
      arrivalDate: this.props.search.trainParam.arrivalDate,
      trainCoach: trainCoach,
      place: placeNumber.target.value
    };
    this.props.savePassengerTicket(newTicket);
  };

  errorMapIterate = mappedErrors => {
    let errorsMessage = [];
    if (Object.keys(mappedErrors).length !== 0) {
      for (var i = 0; i < Object.keys(mappedErrors).length; i++) {
        errorsMessage.push(<p>{mappedErrors[Object.keys(mappedErrors)[i]]}</p>);
      }
      return (
        <div className="alert alert-danger" role="alert" key={i}>
          {errorsMessage}
        </div>
      );
    } else return errorsMessage;
  };

  render() {
    const { stringError } = this.props.error;
    const { mappedErrors } = this.props.error;
    return (
      <div className="popup">
        <div className="popup_inner">
          {stringError && (
            <React.Fragment>
              <h1>{stringError}</h1>
              <div className="popup-row">
                <button
                  className="col btn btn-primary-o"
                  onClick={this.props.closePopup}
                >
                  Cansel
                </button>
              </div>
            </React.Fragment>
          )}
          {!stringError && (
            <React.Fragment>
              <h1>Your ticket</h1>
              {this.errorMapIterate(mappedErrors)}
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
                      {this.props.search.directionParam.departureStation.label}{" "}
                      -
                      {
                        this.props.search.directionParam.destinationStation
                          .label
                      }
                    </h3>

                    <p>
                      <IcoMoon className="icon" icon="road" /> Train number #{" "}
                      {this.props.search.trainParam.id}
                    </p>

                    <p>
                      <IcoMoon className="icon" icon="clock" /> Departure:{" "}
                      {this.props.search.trainParam.departureDate}
                      {" - "}
                      {this.props.search.trainParam.departureTime}
                    </p>

                    <p>
                      <IcoMoon className="icon" icon="clock" /> Arrival:{" "}
                      {this.props.search.trainParam.arrivalDate}
                      {" - "}
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
            </React.Fragment>
          )}
        </div>
      </div>
    );
  }
}

ConfirmBuyingPopup.propTypes = {
  search: PropTypes.object.isRequired,
  error: PropTypes.object.isRequired,
  savePassengerTicket: PropTypes.func.isRequired,
  countTicketPrice: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  ticket: state.ticket,
  search: state.search,
  error: state.error
});

export default connect(
  mapStateToProps,
  { savePassengerTicket, countTicketPrice }
)(ConfirmBuyingPopup);
