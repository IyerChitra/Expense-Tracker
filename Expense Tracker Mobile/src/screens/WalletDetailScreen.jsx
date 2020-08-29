import React from 'react';
import { View, Text } from 'react-native';
import AppHeader from '../components/Header';

const WalletDetailsScreen = ({ navigation }) => (
  <>
    <AppHeader
      title="Wallet Details"
      showLeftIcon={true}
      navigation={navigation}
    />
    <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
      <Text>Wallet Details</Text>
    </View>
  </>
);

export default WalletDetailsScreen;
