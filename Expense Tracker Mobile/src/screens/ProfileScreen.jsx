import React from 'react';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import AppHeader from '../components/Header';
export default class ProfileScreen extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { navigation } = this.props;
    return (
      <>
        <AppHeader
          title="My Profile"
          showLeftIcon={true}
          navigation={navigation}
        />
        <View
          style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}
        >
          <Text>Profile Screen</Text>
        </View>
      </>
    );
  }
}

ProfileScreen.propTypes = {
  // eslint-disable-next-line react/require-default-props
  navigation: PropTypes.func
};
