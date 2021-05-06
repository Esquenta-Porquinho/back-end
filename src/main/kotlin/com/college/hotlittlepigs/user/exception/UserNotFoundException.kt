package com.college.hotlittlepigs.user.exception

import com.college.hotlittlepigs.exception.response.NotFoundException


class UserNotFoundException : NotFoundException("User not found")
