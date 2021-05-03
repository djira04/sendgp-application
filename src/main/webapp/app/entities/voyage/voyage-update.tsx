import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBillet } from 'app/shared/model/billet.model';
import { getEntities as getBillets } from 'app/entities/billet/billet.reducer';
import { IVoyageur } from 'app/shared/model/voyageur.model';
import { getEntities as getVoyageurs } from 'app/entities/voyageur/voyageur.reducer';
import { getEntity, updateEntity, createEntity, reset } from './voyage.reducer';
import { IVoyage } from 'app/shared/model/voyage.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVoyageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VoyageUpdate = (props: IVoyageUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { voyageEntity, billets, voyageurs, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/voyage' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBillets();
    props.getVoyageurs();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.depatureDate = convertDateTimeToServer(values.depatureDate);
    values.depatureTime = convertDateTimeToServer(values.depatureTime);
    values.dateArrival = convertDateTimeToServer(values.dateArrival);
    values.arrivalTime = convertDateTimeToServer(values.arrivalTime);

    if (errors.length === 0) {
      const entity = {
        ...voyageEntity,
        ...values,
        billet: billets.find(it => it.id.toString() === values.billetId.toString()),
        voyageur: voyageurs.find(it => it.id.toString() === values.voyageurId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sengpApp.voyage.home.createOrEditLabel" data-cy="VoyageCreateUpdateHeading">
            <Translate contentKey="sengpApp.voyage.home.createOrEditLabel">Create or edit a Voyage</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : voyageEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="voyage-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="voyage-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="depatureCountryLabel" for="voyage-depatureCountry">
                  <Translate contentKey="sengpApp.voyage.depatureCountry">Depature Country</Translate>
                </Label>
                <AvField id="voyage-depatureCountry" data-cy="depatureCountry" type="text" name="depatureCountry" />
              </AvGroup>
              <AvGroup>
                <Label id="depatureAddressLabel" for="voyage-depatureAddress">
                  <Translate contentKey="sengpApp.voyage.depatureAddress">Depature Address</Translate>
                </Label>
                <AvField id="voyage-depatureAddress" data-cy="depatureAddress" type="text" name="depatureAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="depatureCityLabel" for="voyage-depatureCity">
                  <Translate contentKey="sengpApp.voyage.depatureCity">Depature City</Translate>
                </Label>
                <AvField id="voyage-depatureCity" data-cy="depatureCity" type="text" name="depatureCity" />
              </AvGroup>
              <AvGroup>
                <Label id="depatureDateLabel" for="voyage-depatureDate">
                  <Translate contentKey="sengpApp.voyage.depatureDate">Depature Date</Translate>
                </Label>
                <AvInput
                  id="voyage-depatureDate"
                  data-cy="depatureDate"
                  type="datetime-local"
                  className="form-control"
                  name="depatureDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.voyageEntity.depatureDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="depatureTimeLabel" for="voyage-depatureTime">
                  <Translate contentKey="sengpApp.voyage.depatureTime">Depature Time</Translate>
                </Label>
                <AvInput
                  id="voyage-depatureTime"
                  data-cy="depatureTime"
                  type="datetime-local"
                  className="form-control"
                  name="depatureTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.voyageEntity.depatureTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="arrivalCountryLabel" for="voyage-arrivalCountry">
                  <Translate contentKey="sengpApp.voyage.arrivalCountry">Arrival Country</Translate>
                </Label>
                <AvField id="voyage-arrivalCountry" data-cy="arrivalCountry" type="text" name="arrivalCountry" />
              </AvGroup>
              <AvGroup>
                <Label id="arrivalAddressLabel" for="voyage-arrivalAddress">
                  <Translate contentKey="sengpApp.voyage.arrivalAddress">Arrival Address</Translate>
                </Label>
                <AvField id="voyage-arrivalAddress" data-cy="arrivalAddress" type="text" name="arrivalAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="cityArrivalLabel" for="voyage-cityArrival">
                  <Translate contentKey="sengpApp.voyage.cityArrival">City Arrival</Translate>
                </Label>
                <AvField id="voyage-cityArrival" data-cy="cityArrival" type="text" name="cityArrival" />
              </AvGroup>
              <AvGroup>
                <Label id="dateArrivalLabel" for="voyage-dateArrival">
                  <Translate contentKey="sengpApp.voyage.dateArrival">Date Arrival</Translate>
                </Label>
                <AvInput
                  id="voyage-dateArrival"
                  data-cy="dateArrival"
                  type="datetime-local"
                  className="form-control"
                  name="dateArrival"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.voyageEntity.dateArrival)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="arrivalTimeLabel" for="voyage-arrivalTime">
                  <Translate contentKey="sengpApp.voyage.arrivalTime">Arrival Time</Translate>
                </Label>
                <AvInput
                  id="voyage-arrivalTime"
                  data-cy="arrivalTime"
                  type="datetime-local"
                  className="form-control"
                  name="arrivalTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.voyageEntity.arrivalTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="kilosLabel" for="voyage-kilos">
                  <Translate contentKey="sengpApp.voyage.kilos">Kilos</Translate>
                </Label>
                <AvField id="voyage-kilos" data-cy="kilos" type="string" className="form-control" name="kilos" />
              </AvGroup>
              <AvGroup>
                <Label id="unitPriceLabel" for="voyage-unitPrice">
                  <Translate contentKey="sengpApp.voyage.unitPrice">Unit Price</Translate>
                </Label>
                <AvField id="voyage-unitPrice" data-cy="unitPrice" type="string" className="form-control" name="unitPrice" />
              </AvGroup>
              <AvGroup check>
                <Label id="validLabel">
                  <AvInput id="voyage-valid" data-cy="valid" type="checkbox" className="form-check-input" name="valid" />
                  <Translate contentKey="sengpApp.voyage.valid">Valid</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="voyage-billet">
                  <Translate contentKey="sengpApp.voyage.billet">Billet</Translate>
                </Label>
                <AvInput id="voyage-billet" data-cy="billet" type="select" className="form-control" name="billetId">
                  <option value="" key="0" />
                  {billets
                    ? billets.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="voyage-voyageur">
                  <Translate contentKey="sengpApp.voyage.voyageur">Voyageur</Translate>
                </Label>
                <AvInput id="voyage-voyageur" data-cy="voyageur" type="select" className="form-control" name="voyageurId">
                  <option value="" key="0" />
                  {voyageurs
                    ? voyageurs.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/voyage" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  billets: storeState.billet.entities,
  voyageurs: storeState.voyageur.entities,
  voyageEntity: storeState.voyage.entity,
  loading: storeState.voyage.loading,
  updating: storeState.voyage.updating,
  updateSuccess: storeState.voyage.updateSuccess,
});

const mapDispatchToProps = {
  getBillets,
  getVoyageurs,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VoyageUpdate);
