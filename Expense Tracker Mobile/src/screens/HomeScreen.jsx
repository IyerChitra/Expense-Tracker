import React from 'react';
import { View, Text } from 'react-native';
import { Button } from 'react-native-elements';
import Icon from 'react-native-vector-icons/FontAwesome';
import PropTypes from 'prop-types';

const HomeScreen = ({ navigation }) => (
  <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
    <Text>Home Screen</Text>
    <Button
      icon={<Icon name="arrow-right" size={15} color="white" />}
      title="Go to Profile"
      onPress={() => navigation.navigate('Profile')}
    />
  </View>
);

HomeScreen.propTypes = {
  // eslint-disable-next-line react/require-default-props
  navigation: PropTypes.func
};
export default HomeScreen;
