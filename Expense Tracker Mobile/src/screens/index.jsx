import { createStackNavigator } from '@react-navigation/stack';
import React from 'react';
import HomeScreen from './HomeScreen';
import ProfileScreen from './ProfileScreen';
import WalletsScreen from './WalletsScreen';
import WalletDetailScreen from './WalletDetailScreen';

const Stack = createStackNavigator();

const AppContext = React.createContext('');

const StackNavigator = () => (
  <AppContext.Provider>
    <Stack.Navigator initialRouteName="Home">
      <Stack.Screen
        name="Home"
        component={HomeScreen}
        options={{ title: 'Overview' }}
      />
      <Stack.Screen name="Profile" component={ProfileScreen} />
      <Stack.Screen name="Wallets" component={WalletsScreen} />
      <Stack.Screen name="WalletDetailScreen" component={WalletDetailScreen} />
    </Stack.Navigator>
  </AppContext.Provider>
);
export default StackNavigator;
