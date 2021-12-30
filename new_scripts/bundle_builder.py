from utils import number_to_end
import re

class BundleBuilder:
    def __init__(self, name, flag_type, app_type) -> None:
        self.name = name
        self.flag_type = flag_type
        self.app_type = app_type
        pass

    # Gets the bundle prefix
    def __get_prefix(self):
        return "br.com."

    # Gets the app name
    def __get_bundle_app_name(self):
        lower_name = self.name.lower()
        # Removes special character ( . , # $ % )
        corrected_name = re.sub('[\W_]+', '', lower_name)
        return number_to_end(corrected_name)
    
    # Returns the flag type identifier (Driver or Taxi)
    def __get_flag_type_identifier(self):
        if (self.flag_type == "Driver"):
            return "drivermachine"
        elif (self.flag_type == "Taxi"):
            return "taximachine"
        else:
            return ""

    # Returns the app type identifier (passenger or taxi)
    def __get_app_type_identifier(self):
        if (self.app_type == "passenger"):
            return "passenger"
        elif (self.app_type == "taxi"):
            return "taxi"

    def get_bundle(self):
        return self.__get_prefix() + self.__get_bundle_app_name() + "." + self.__get_app_type_identifier() + "." + self.__get_flag_type_identifier()