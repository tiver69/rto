import React, { Component } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import LineSearchForm from "./LineSearchForm";
import TrainTable from "./TrainTable";

class BookingTrain extends Component {
  render() {
    const { trains } = this.props.train;

    return (
      <React.Fragment>
        <LineSearchForm />
        {/* <!-- main section --> */}
        <div className="site-section">
          <div className="container">
            <div className="row">
              <div className="col">
                {/* <!-- train list section --> */}
                <h2 className="mb-5 text-primary-o">Results</h2>
                <div className="limiter">
                  <div className="wrap-table100">
                    <div className="table100 ver4 m-b-110">
                      <table data-vertable="ver4">
                        <thead>
                          <tr className="row100 head">
                            <th
                              className="column100 column1"
                              data-column="column1"
                            >
                              Number
                            </th>
                            <th
                              className="column100 column2"
                              data-column="column2"
                            >
                              From/To
                            </th>
                            <th
                              className="column100 column4"
                              data-column="column4"
                            >
                              Departure
                            </th>
                            <th
                              className="column100 column5"
                              data-column="column5"
                            >
                              Arrival
                            </th>
                            <th
                              className="column100 column6"
                              data-column="column6"
                            >
                              Duration
                            </th>
                            <th
                              className="column100 column7"
                              data-column="column7"
                            >
                              Seats Available
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                          {trains.map(train => (
                            <TrainTable
                              key={train.id}
                              train={train}
                              history={this.props.history}
                            />
                          ))}
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

BookingTrain.propTypes = {
  train: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  train: state.train
});

export default connect(mapStateToProps)(BookingTrain);
