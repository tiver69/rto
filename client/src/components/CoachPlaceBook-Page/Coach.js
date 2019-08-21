import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import ConfirmBuyingPopup from "./ConfirmBuyingPopup";

class Coach extends Component {
  constructor() {
    super();
    this.state = {
      justBoughtPlaces: [],
      showPopup: false,
      placeNumber: ""
    };
    this.togglePopup = this.togglePopup.bind(this);
  }

  getRowByTypeId = typeId => {
    let rows = [];
    switch (typeId) {
      case 1:
        rows.push(2);
        rows.push(4);
        break;
      case 2:
        rows.push(3);
        rows.push(5);
        break;
      case 3:
        rows.push(2);
        rows.push(2);
        break;
      case 4:
        rows.push(2);
        rows.push(3);
        break;
      case 5:
        rows.push(1);
        rows.push(1);
        break;
      default:
        rows.push(0);
        rows.push(0);
    }
    return rows;
  };

  getPlaceView = (placeNumber, bookedPlaces) => {
    if (
      bookedPlaces.includes(placeNumber) ||
      this.state.justBoughtPlaces.includes(placeNumber)
    ) {
      return (
        <div
          className="col place disable"
          title="Booked"
          key={"place#" + placeNumber}
        >
          {placeNumber}
        </div>
      );
    } else {
      return (
        <button
          className="col place a-button"
          title="Free"
          value={placeNumber}
          key={"place#" + placeNumber}
          onClick={this.togglePopup}
        >
          {placeNumber}
        </button>
      );
    }
  };

  togglePopup(e) {
    e.preventDefault();
    if (this.state.showPopup)
      this.setState({
        placeNumber: "",
        showPopup: !this.state.showPopup
      });
    else
      this.setState({
        placeNumber: e.target.value,
        showPopup: !this.state.showPopup
      });
  }

  createCoachView = (number, typeId, bookedPlaces) => {
    let coach = [];
    let rows = this.getRowByTypeId(typeId);

    let placePerRow = number / rows[1];
    for (let i = 0; i < rows[0]; i++) {
      let coachRow = [];
      for (let j = 0; j < placePerRow; j++) {
        coachRow.push(this.getPlaceView(j * rows[1] + i + 1, bookedPlaces));
      }
      coach.push(
        <div className="row" key={"row#" + i}>
          {coachRow}
        </div>
      );
    }

    coach.push(
      <div className="row" key="pass">
        <div className="col place pass" title="Pass" />
      </div>
    );

    for (let i = rows[0]; i < rows[1]; i++) {
      let coachRow = [];
      for (let j = 0; j < placePerRow; j++) {
        coachRow.push(this.getPlaceView(j * rows[1] + i + 1, bookedPlaces));
      }
      coach.push(
        <div className="row" key={"row#" + i}>
          {coachRow}
        </div>
      );
    }

    return coach;
  };

  componentWillReceiveProps(nextProps) {
    // TO_DO: handle errors
    if (nextProps.ticket.ticket) {
      this.state.justBoughtPlaces.push(nextProps.ticket.ticket.place);
    }
  }

  render() {
    const { coach } = this.props;
    return (
      <React.Fragment>
        <div className="col-lg-4 mt-5 text-left">
          <li key={coach.number}>
            <span>
              {coach.name} {coach.availablePlaces}/{coach.totalPlaces}
            </span>
          </li>
        </div>
        <hr />
        {/* <!-- coache places section --> */}
        <div className="wagon-floors">
          <div className="floor floor-1">
            {this.createCoachView(
              coach.totalPlaces,
              coach.typeId,
              coach.bookedPlaces
            )}
          </div>
        </div>
        {this.state.showPopup ? (
          <ConfirmBuyingPopup
            coach={coach}
            text={this.state.placeNumber}
            closePopup={this.togglePopup.bind(this)}
            history={this.props.history}
          />
        ) : null}
      </React.Fragment>
    );
  }
}

Coach.propTypes = {
  ticket: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  ticket: state.ticket
});

export default connect(
  mapStateToProps,
  {}
)(Coach);
