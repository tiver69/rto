import React, { Component } from "react";
import IcoMoon from "react-icomoon";
import { connect } from "react-redux";
import classnames from "classnames";
import { getStations } from "../../actions/stationActions";
import { searchForTrain } from "../../actions/trainActions";
import { currentDate } from "../../util/functions";
import { setDirectionSearchParam } from "../../actions/searchParamActions";
import PropTypes from "prop-types";
import Select from "react-select";

class SearchForm extends Component {
  constructor() {
    super();
    this.state = {
      departureDate: currentDate(),
      departureStation: { value: "1", label: "Zaporizhzhya 1" },
      destinationStation: { value: "2", label: "Kyiv-Pasazhyrsky" },
      mappedErrors: {}
    };
    this.onSubmit = this.onSubmit.bind(this);
    this.onSwitchButton = this.onSwitchButton.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  onSwitchButton() {
    var buffStation = this.state.departureStation;
    this.setState({
      departureStation: this.state.destinationStation,
      destinationStation: buffStation
    });
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  componentDidMount() {
    this.props.getStations();
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.error) {
      this.setState({ mappedErrors: nextProps.error.mappedErrors });
    }
  }

  async onSubmit(e) {
    e.preventDefault();
    const direction = {
      departureDate: this.state.departureDate,
      departureStation: this.state.departureStation,
      destinationStation: this.state.destinationStation
    };
    this.props.searchForTrain(
      direction.departureStation.value,
      direction.destinationStation.value,
      direction.departureDate
    );
    this.props.setDirectionSearchParam(direction);
    if (Object.keys(this.state.mappedErrors).length === 0) {
      this.props.history.push("/booking/train");
    }
  }

  handleDepartureChange = departureStation => {
    this.setState({ departureStation });
  };

  handleDestinationChange = destinationStation => {
    this.setState({ destinationStation });
  };

  render() {
    const { stations } = this.props.station;
    const { mappedErrors } = this.state;

    return (
      <div className="col-lg-3 ml-auto">
        <div className="mb-5">
          <h3 className="h5 text-black mb-3">
            Booking tickets{" "}
            <button
              className="f-right p-color a-button"
              onClick={this.onSwitchButton}
            >
              <IcoMoon icon="tab" />
            </button>
          </h3>

          <form onSubmit={this.onSubmit}>
            <IcoMoon icon="location" /> Departure station
            <div className="form-group">
              {mappedErrors.departureStation && (
                <div className="alert alert-danger alert-search" role="alert">
                  {mappedErrors.departureStation}
                </div>
              )}
              <div className="select-wrap">
                <Select
                  className={classnames("", {
                    "alert-select-border": mappedErrors.destinationStation
                  })}
                  value={this.state.departureStation}
                  onChange={this.handleDepartureChange}
                  options={stations}
                />
              </div>
            </div>
            <IcoMoon icon="location" /> Destination station
            <div className="form-group">
              {mappedErrors.destinationStation && (
                <div className="alert alert-danger alert-search" role="alert">
                  {mappedErrors.destinationStation}
                </div>
              )}
              <div className="select-wrap">
                <Select
                  className={classnames("", {
                    "alert-select-border": mappedErrors.destinationStation
                  })}
                  value={this.state.destinationStation}
                  onChange={this.handleDestinationChange}
                  options={stations}
                />
              </div>
            </div>
            <div className="form-group">
              <IcoMoon icon="calendar" /> Date:{" "}
              {mappedErrors.departureDate && (
                <div className="alert alert-danger alert-search" role="alert">
                  {mappedErrors.departureDate}
                </div>
              )}
              <input
                type="date"
                name="departureDate"
                min={currentDate()}
                required="required"
                className={classnames("form-control", {
                  "alert-select-border": mappedErrors.destinationStation
                })}
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
  train: PropTypes.object.isRequired,
  getStations: PropTypes.func.isRequired,
  searchForTrain: PropTypes.func.isRequired,
  setDirectionSearchParam: PropTypes.func.isRequired,
  error: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  station: state.station,
  train: state.train,
  error: state.error
});

export default connect(
  mapStateToProps,
  { getStations, searchForTrain, setDirectionSearchParam }
)(SearchForm);
