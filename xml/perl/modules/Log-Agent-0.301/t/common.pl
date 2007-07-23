###########################################################################
# $Id: common.pl,v 1.2 2002/06/26 18:20:13 sirott Exp $
###########################################################################
#
# common.pl
#
# RCS Revision: $Revision: 1.2 $
# Date: $Date: 2002/06/26 18:20:13 $
#
# Copyright (C) 1999 Raphael Manfredi.
# Copyright (C) 2002 Mark Rogaski, mrogaski@cpan.org; all rights reserved.
#
# See the README file included with the
# distribution for license information.
#
# $Log: common.pl,v $
# Revision 1.2  2002/06/26 18:20:13  sirott
# 	* Merged v_6_0_base_branch with main branch
#
# Revision 1.1.2.1  2002/06/02 01:10:59  sirott
# 	* Added all required Perl modules to CVS distribution
#
# Revision 1.1  2002/02/23 06:26:10  wendigo
# Initial revision
#
#
###########################################################################

sub contains ($$) {
    my ($file, $pattern) = @_;
    local *FILE;
    local $_;
    open(FILE, $file) || die "can't open $file: $!\n";
    my $found = 0;
    my $line = 0;
    while (<FILE>) {
        $line++;
        if (/$pattern/) {
            $found = 1;
            last;
        }
    }
    close FILE;
    return $found ? $line : 0;
}

sub perm_ok ($$) {
    #
    # Given a fileame and target permissions, checks if the file
    # was created with the correct permissions.
    #
    my($file, $target) = @_;

    $target &= ~ umask;         # account for user mask 
    my $mode = (stat $file)[2]; # find the current mode
    $mode &= 0777;              # we only care about UGO

    return $mode == $target;
}

1;

