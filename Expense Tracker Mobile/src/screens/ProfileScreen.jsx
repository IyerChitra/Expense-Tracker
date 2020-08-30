import React from 'react';
import PropTypes from 'prop-types';
import AppHeader from '../components/Header';
import { Avatar } from 'react-native-elements';
import MIcon from 'react-native-vector-icons/MaterialIcons';
import MCIcon from 'react-native-vector-icons/MaterialCommunityIcons';
import FIcon from 'react-native-vector-icons/FontAwesome';
import { Input, Button } from 'react-native-elements';
import { ScrollView } from 'react-native';

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

        <Avatar
          rounded
          size={70}
          overlayContainerStyle={{ backgroundColor: 'grey' }}
          icon={{ name: 'user', type: 'font-awesome', color: 'white' }}
          onPress={() => console.log('Works!')}
          containerStyle={{ marginLeft: 10, marginTop: 20 }}
        />

        <ScrollView showsVerticalScrollIndicator={false}>
          <Input
            placeholder="Email Address"
            label="Email"
            leftIcon={<MIcon name="email" size={24} color="black" />}
            containerStyle={{ paddingTop: 30 }}
          />

          <Input
            placeholder="Username"
            label="Username"
            leftIcon={<FIcon name="user" size={24} color="black" />}
          />

          <Input
            placeholder="Password"
            label="Password"
            secureTextEntry={true}
            leftIcon={
              <MCIcon name="form-textbox-password" size={24} color="black" />
            }
          />

          <Button title="Update Profile" type="clear" />
        </ScrollView>
      </>
    );
  }
}

ProfileScreen.propTypes = {
  // eslint-disable-next-line react/require-default-props
  navigation: PropTypes.object
};
