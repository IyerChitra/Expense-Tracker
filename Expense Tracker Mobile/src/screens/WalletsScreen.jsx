import React, { Component } from 'react';
import { View } from 'react-native';
import WalletsComponent from '../components/Wallets';
import AppHeader from '../components/Header';

export default class WalletsScreen extends Component {
  constructor(props) {
    super(props);
  }
  static navigationOptions = {
    header: { visible: false }
  };

  render() {
    const { navigation } = this.props;
    return (
      <>
        <AppHeader title="Wallets" />
        <View style={{ flex: 1 }}>
          <WalletsComponent navigation={navigation} />
        </View>
      </>
    );
  }
}
