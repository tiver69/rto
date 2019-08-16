import React, { Component } from "react";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import LineSearchForm from "../Item/LineSearchForm";
import TrainTable from "../Item/TrainTable";

class BookingTrain extends Component {
  componentDidMount() {
    // console.log(this.props.train);
  }

  render() {
    const { trains } = this.props.train;
    {
      console.log(trains);
    }

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

                <form action="trainDetail" method="get">
                  <input
                    id="departureHidden"
                    name="departureStationHidden"
                    type="hidden"
                  />
                  <input
                    id="destinationHidden"
                    name="destinationStationHidden"
                    type="hidden"
                  />
                  <input
                    id="dateHidden"
                    name="departureDateHidden"
                    type="hidden"
                    value="${pageContext.request.getParameter('departureDate')}"
                  />

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
                                booking.button
                              </th>
                              <th
                                className="column100 column2"
                                data-column="column2"
                              >
                                direction
                              </th>
                              <th
                                className="column100 column4"
                                data-column="column4"
                              >
                                departure
                              </th>
                              <th
                                className="column100 column5"
                                data-column="column5"
                              >
                                booking.arrival
                              </th>
                              <th
                                className="column100 column6"
                                data-column="column6"
                              >
                                booking.duration
                              </th>
                              <th
                                className="column100 column7"
                                data-column="column7"
                              >
                                booking.seats
                              </th>
                            </tr>
                          </thead>
                          <tbody>
                            {trains.map(train => (
                              <TrainTable key={train.id} train={train} />
                            ))}
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </form>
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
