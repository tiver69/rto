import React, { Component } from "react";

class TrainTable extends Component {
  render() {
    const { train } = this.props;

    return (
      <tr className="row100">
        <td className="column100 column1" data-column="column1">
          <input
            type="submit"
            className="btn btn-primary-o"
            name="selectedTrainId"
            value={train.id}
          />
        </td>

        <td className="column100 column2" data-column="column2">
          {train.firstStationName} - {train.lastStationName}
        </td>

        <td className="column100 column4" data-column="column4">
          {train.departureTime}
        </td>
        <td className="column100 column5" data-column="column5">
          {train.arrivalTime}
        </td>

        <td className="column100 column6" data-column="column6">
          {train.duration}
        </td>
        <td className="column100 column7" data-column="column7">
          <table>
            <tbody>
              <tr>
                <td>coachtype : quantity </td>
                <td>awailable / total</td>
              </tr>
            </tbody>
          </table>
        </td>
      </tr>
    );
  }
}

export default TrainTable;
