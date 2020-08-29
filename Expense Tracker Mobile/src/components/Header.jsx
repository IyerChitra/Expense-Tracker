import React from 'react';
import { Header } from 'react-native-elements';
import IonIcons from 'react-native-vector-icons/Ionicons';
import { TouchableOpacity } from 'react-native';

const AppHeader = ({ title, showLeftIcon, navigation }) => (
  <Header
    statusBarProps={{ barStyle: 'light-content' }}
    barStyle="light-content"
    centerComponent={{
      text: title,
      style: { color: '#000', fontSize: 18, fontWeight: 'bold' }
    }}
    containerStyle={{
      backgroundColor: '#edce79',
      justifyContent: 'space-around'
    }}
    leftComponent={
      showLeftIcon ? <LeftComponent navigation={navigation} /> : null
    }
  />
);

const LeftComponent = ({ navigation }) => (
  <TouchableOpacity onPress={() => navigation.goBack()}>
    <IonIcons
      name="arrow-back"
      color="#000"
      size={30}
      style={{ paddingLeft: 5, paddingRight: 10 }}
    />
  </TouchableOpacity>
);

export default AppHeader;
