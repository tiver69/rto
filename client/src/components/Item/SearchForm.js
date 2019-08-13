import React, { Component } from "react";
import IcoMoon from "react-icomoon";
import { connect } from "react-redux";
import { getStations } from "../../actions/stationActions";
import PropTypes from "prop-types";
import Select from "react-select";

class SearchForm extends Component {
  constructor() {
    super();
    this.state = {
      departureDate: "",
      departureStation: 1,
      destinationStation: 2
    };
    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
    // console.log(e.target.value);
  }

  componentDidMount() {
    this.props.getStations();
  }

  onSubmit(e) {
    e.preventDefault();
    const trainSearch = {
      departureDate: this.state.departureDate,
      departureStation: this.state.departureStation.value,
      destinationStation: this.state.destinationStation.value
    };
    console.log(trainSearch);
  }

  handleDepartureChange = departureStation => {
    this.setState({ departureStation });
    // console.log(`Option1 selected:`, departureStation);
  };

  handleDestinationChange = destinationStation => {
    this.setState({ destinationStation });
    // console.log(`Option2 selected:`, destinationStation);
  };
  render() {
    const { stations } = this.props.station;
    return (
      <div className="col-lg-3 ml-auto">
        <div className="mb-5">
          <h3 className="h5 text-black mb-3">
            Booking tickets{" "}
            <button className="f-right p-color a-button">
              <IcoMoon icon="tab" />
            </button>
          </h3>

          <form onSubmit={this.onSubmit}>
            <IcoMoon icon="location" /> Departure station
            <div className="form-group">
              <div className="select-wrap">
                <span className="icon">
                  <IcoMoon icon="menu" />
                </span>
                <Select
                  value={this.state.departureStation}
                  onChange={this.handleDepartureChange}
                  options={stations}
                />
              </div>
            </div>
            <IcoMoon icon="location" /> Destination station
            <div className="form-group">
              <div className="select-wrap">
                <span className="icon">
                  <IcoMoon icon="menu" />
                </span>
                <Select
                  value={this.state.destinationStation}
                  onChange={this.handleDestinationChange}
                  options={stations}
                />
              </div>
            </div>
            <div className="form-group">
              Date:{" "}
              <input
                type="date"
                name="departureDate"
                required="required"
                className="form-control"
                value={this.state.departureDate}
                onChange={this.onChange}
              />
            </div>
            <div className="form-group">
              <input
                type="submit"
                value="Search and Order"
                className="btn btn-primary-o"
              />
            </div>
          </form>
        </div>
      </div>
    );
  }
}

SearchForm.propTypes = {
  station: PropTypes.object.isRequired,
  getStations: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  station: state.station
});

export default connect(
  mapStateToProps,
  { getStations }
)(SearchForm);
