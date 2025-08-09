import LoginKit, {
  LoginState,
  UserData,
  UserDataScopes,
  VerifyResponse,
} from '@snapchat/snap-kit-react-native';
import React, {useState} from 'react';
import {
  Alert,
  Button,
  DeviceEventEmitter,
  Dimensions,
  Image,
  Modal,
  NativeEventEmitter,
  NativeModules,
  Platform,
  Pressable,
  SafeAreaView,
  ScrollView,
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';
var {width} = Dimensions.get('window');

export default function App() {
  const [lastLoginState, setLastLoginState] = useState<LoginState>();
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [refreshAccessToken, setRefreshAccessToken] = useState<string | null>(
    '',
  );
  const [hasAccessToScope, setHasAccessToScope] = useState<boolean>(false);
  const [userData, setUserData] = useState<UserData | undefined>();
  const [phoneNumber, setPhoneNumber] = useState<string>('');
  const [countryCode, setCountryCode] = useState<string>('');
  const [verifySuccess, setVerifySuccess] = useState<boolean | null>(null);
  const [verifyError, setVerifyError] = useState<string | null>(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [isVerifyOnly, setIsVerifyOnly] = useState(false);

  React.useEffect(() => {
    const onLoginStateChange = (state: LoginState) => {
      setLastLoginState(state);
      getIsUserLoggedInStatus();
      getRefreshAccessToken();
      getHasAccessToUserDisplayName();
      fetchUserData();
    };

    const EventEmitter = Platform.select({
      ios: new NativeEventEmitter(NativeModules.LoginKit),
      android: DeviceEventEmitter,
    });

    EventEmitter?.addListener(LoginState.LOGIN_KIT_LOGIN_STARTED, () =>
      onLoginStateChange(LoginState.LOGIN_KIT_LOGIN_STARTED),
    );
    EventEmitter?.addListener(LoginState.LOGIN_KIT_LOGIN_SUCCEEDED, () =>
      onLoginStateChange(LoginState.LOGIN_KIT_LOGIN_SUCCEEDED),
    );
    EventEmitter?.addListener(LoginState.LOGIN_KIT_LOGIN_FAILED, () =>
      onLoginStateChange(LoginState.LOGIN_KIT_LOGIN_FAILED),
    );
    EventEmitter?.addListener(LoginState.LOGIN_KIT_LOGOUT, () =>
      onLoginStateChange(LoginState.LOGIN_KIT_LOGOUT),
    );

    getIsUserLoggedInStatus();
    getRefreshAccessToken();
    getHasAccessToUserDisplayName();
    fetchUserData();

    return () => {
      DeviceEventEmitter.removeAllListeners();
    };
  }, []);

  const verifyResult = (verifyResponse: VerifyResponse) => {
    const URL = `https://api.snapkit.com/v1/phoneverify/verify_result?phone_number_id=${verifyResponse.phoneId}&phone_number_verify_id=${verifyResponse.verifyId}&phone_number=${phoneNumber}&region=${countryCode}`;

    fetch(URL, {
      method: 'POST',
    })
      .then((response) => response.json())
      .then((responseJSON) => {
        setVerifySuccess(responseJSON.verified);
        setVerifyError(null);
      });
  };

  const getRefreshAccessToken = () => {
    LoginKit.refreshAccessToken()
      .then((accessToken) => setRefreshAccessToken(accessToken))
      .catch((error) => setRefreshAccessToken(error));
  };

  const getIsUserLoggedInStatus = () => {
    LoginKit.isUserLoggedIn().then((isUserLoggedIn) =>
      setIsLoggedIn(isUserLoggedIn),
    );
  };

  const getHasAccessToUserDisplayName = () => {
    LoginKit.hasAccessToScope(UserDataScopes.DISPLAY_NAME).then((hasAccess) =>
      setHasAccessToScope(hasAccess),
    );
  };

  const fetchUserData = () => {
    const query = '{me{bitmoji{avatar},displayName}}';

    LoginKit.fetchUserData(query, null)
      .then((data) => {
        setUserData(data as UserData);
      })
      .catch((error) => {
        setUserData(error);
      });
  };

  return (
    <View style={styles.container}>
      <SafeAreaView />

      <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          Alert.alert('Modal has been closed.');
          setModalVisible(!modalVisible);
        }}>
        <View style={styles.centeredView}>
          <View style={styles.modalView}>
            <Text style={styles.modalText}>What's your Mobile Number?</Text>

            <View style={{flexDirection: 'row'}}>
              <TextInput
                value={countryCode}
                onChangeText={(country) => setCountryCode(country)}
                placeholder={'Country Code'}
                style={{flex: 0.5, textAlign: 'center'}}
              />
              <TextInput
                value={phoneNumber}
                onChangeText={(number) => setPhoneNumber(number)}
                placeholder={'Phone Number'}
                style={{flex: 0.5, textAlign: 'center'}}
              />
            </View>

            <Text style={{padding: 20, color: verifySuccess ? 'green' : 'red'}}>
              {verifySuccess != null
                ? `Phone Verified? ${verifySuccess}`
                : verifyError != null && `${verifyError}`}
            </Text>

            <View style={{flexDirection: 'row'}}>
              {verifySuccess === null && (
                <Pressable
                  style={[styles.button, styles.buttonClose]}
                  onPress={() => setModalVisible(!modalVisible)}>
                  <Text style={styles.textStyle}>Cancel</Text>
                </Pressable>
              )}

              <Pressable
                style={[styles.button, styles.buttonClose]}
                onPress={() => {
                  if (verifySuccess != null) {
                    setModalVisible(false);
                  } else {
                    if (isVerifyOnly) {
                      LoginKit.verify(phoneNumber, countryCode)
                        .then(async (response) => {
                          await verifyResult(response);
                        })
                        .catch((error) => {
                          setVerifySuccess(null);
                          setVerifyError(error);
                        });
                    } else {
                      LoginKit.verifyAndLogin(phoneNumber, countryCode)
                        .then(async (response) => {
                          await verifyResult(response);
                        })
                        .catch((error) => {
                          setVerifySuccess(null);
                          setVerifyError(error);
                        });
                    }
                  }
                }}>
                <Text style={styles.textStyle}>OK</Text>
              </Pressable>
            </View>
          </View>
        </View>
      </Modal>

      <View style={styles.imageContainer}>
        {userData?.bitmojiAvatar ? (
          <Image
            source={{
              uri: userData!.bitmojiAvatar,
            }}
            style={styles.image}
          />
        ) : (
          <Text>No Bitmoji avatar</Text>
        )}
        <Text style={styles.displayNameText}>
          {userData?.displayName ?? ''}
        </Text>
      </View>
      <View style={styles.scrollViewContainer}>
        <ScrollView contentContainerStyle={styles.contentContainerStyle}>
          <Text style={styles.text}>
            <Text style={{fontWeight: 'bold'}}>{`lastLoginState: `}</Text>
            {`${lastLoginState}`}
          </Text>
          <Text style={styles.text}>
            <Text style={{fontWeight: 'bold'}}>{`isUserLoggedIn: `}</Text>
            {`${isLoggedIn}`}
          </Text>
          <Text style={styles.text}>
            <Text style={{fontWeight: 'bold'}}>{`accessToken: `}</Text>
            {`${JSON.stringify(refreshAccessToken, null, 2)}`}
          </Text>
          <Text style={styles.text}>
            <Text
              style={{
                fontWeight: 'bold',
              }}>{`hasAccessToScope - user.display_name: `}</Text>
            {`${hasAccessToScope}`}
          </Text>
          <Text style={styles.text}>
            <Text style={{fontWeight: 'bold'}}>{`userData: `}</Text>
            {`${JSON.stringify(userData, null, 2)}`}
          </Text>
        </ScrollView>
      </View>

      <View style={styles.buttonRowContainer}>
        <View style={styles.buttonContainer}>
          <Button
            title={'Verify'}
            onPress={() => {
              setVerifySuccess(null);
              setVerifyError(null);
              setIsVerifyOnly(true);
              setModalVisible(true);
            }}
          />
        </View>
        <View style={styles.buttonContainer}>
          <Button
            title={'Verify & Login'}
            onPress={() => {
              setVerifySuccess(null);
              setVerifyError(null);
              setIsVerifyOnly(false);
              setModalVisible(true);
            }}
          />
        </View>
      </View>
      <View style={styles.buttonRowContainer}>
        <View style={styles.buttonContainer}>
          <Button title={'Login'} onPress={() => LoginKit.login()} />
        </View>
        <View style={styles.buttonContainer}>
          <Button
            title={'Log Out'}
            onPress={() => {
              LoginKit.clearToken();
              setUserData({});
              setRefreshAccessToken('');
            }}
          />
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    height: '100%',
  },
  text: {
    paddingBottom: 10,
  },
  scrollViewContainer: {
    flex: 1,
  },
  contentContainerStyle: {
    padding: 10,
  },
  image: {
    width: width * 0.4,
    height: width * 0.4,
    borderRadius: 100,
  },
  imageContainer: {
    flexDirection: 'row',
  },
  displayNameText: {
    fontSize: 30,
    color: 'green',
  },
  buttonRowContainer: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    width: '100%',
    justifyContent: 'space-between',
    flex: 0.1,
    marginTop: 5,
  },
  buttonContainer: {
    width: '45%',
    margin: 5,
  },
  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 22,
  },
  modalView: {
    margin: 20,
    backgroundColor: 'white',
    borderRadius: 20,
    padding: 35,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  button: {
    borderRadius: 20,
    padding: 10,
    elevation: 2,
    margin: 10,
    width: '40%',
  },
  buttonOpen: {
    backgroundColor: '#F194FF',
  },
  buttonClose: {
    backgroundColor: '#2196F3',
  },
  textStyle: {
    color: 'white',
    fontWeight: 'bold',
    textAlign: 'center',
  },
  modalText: {
    marginBottom: 25,
    textAlign: 'center',
    fontWeight: 'bold',
  },
});
